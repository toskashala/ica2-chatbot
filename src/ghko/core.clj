(ns ghko.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.data.json :as json]
            [ghko.keywords :as keys]
            [ghko.responses :as r]
            ;; [ghko.reply :as rep]
            ))

(def park-json (io/resource "park.json"))
;; (def trees (io/resource "trees.json"))

(def parks_of_prague (json/read-json (slurp park-json)))

;; (json/read-json (slurp trees))

(require '[clojure.string :as str])


;;predicates
(defn wc? [park]
  (:wc (park parks_of_prague)))

(defn biking? [park]
  (:biking (park parks_of_prague)))

(defn skating? [park]
  (:skating (park parks_of_prague)))

(defn sports? [park]
  (:sports (park parks_of_prague)))

(defn playground? [park]
  (:playground (park parks_of_prague)))

(defn parking? [park]
  (:parking (park parks_of_prague)))

(defn dogs? [park]
  (:dogs (park parks_of_prague)))

(defn website [park]
  (:website (park parks_of_prague)))

(defn normalize_string [string]
  (re-find #".*[A-Za-z]" (str/lower-case string)))

(defn string_to_vector [string]
  (str/split string #" "))

(defn arr_contains? [uarr search]
  (let [stop (dec (count uarr))]
    (loop [i 0]
      (let [current (nth uarr i)]
        (if (= (normalize_string current) search)
          true
          (if (< i stop)
            (recur (inc i))
            false))))))


(defn normalize_key [key]
  (re-find #"[A-Za-z].*" (str key)))

(defn reply_wc [park]
  (def i_wc 1)
  (cond
    (wc? park)
    (println (:1 (:wc_yes r/responses)))
    (not (wc? park))
    (println (:1 (:wc_no r/responses)))))

(defn reply_dogs [park]
  (def i_dogs 1)
  (cond
    (dogs? park)
    (println (:1 (:dogs_yes r/responses)))
    (not (dogs? park))
    (println (:1 (:dogs_no r/responses)))))

(defn reply_attractions [park]
  (def i_int 1)
  (println "There are" (:attractions (park parks_of_prague))))

(defn reply_transportation [park]
  (def i_tra 1)
  (println "To get to the park use" (:transportation (park parks_of_prague))))

(defn reply_biking [park]
  (def i_bik 1)
  (cond
    (biking? park)
    (println (:1 (:biking_yes r/responses)))
    (not (biking? park))
    (println (:1 (:biking_no r/responses)))))

(defn reply_skating [park]
  (def i_ska 1)
  (cond
    (skating? park)
    (println (:1 (:skating_yes r/responses)))
    (not (skating? park))
    (println (:1 (:skating_no r/responses)))))

(defn reply_sports [park]
  (def i_spo 1)
  (cond
    (sports? park)
    (println (:1 (:sports_yes r/responses)))
    (not (sports? park))
    (println (:1 (:sports_no r/responses)))))


(defn reply_playground [park]
  (def i_pla 1)
  (cond
    (playground? park)
    (println (:1 (:playground_yes r/responses)))
    (not (playground? park))
    (println (:1 (:playground_no r/responses)))))

(defn reply_parking [park]
  (def i_par 1)
  (cond
    (parking? park)
    (println (:1 (:parking_yes r/responses)))
    (not (parking? park))
    (println (:1 (:parking_no r/responses)))))

(defn reply_website [park]
  (def i_web 1)
  (println "For more information" (normalize_key park) "visit" (website park)))


;;The user is able to change the park atsany time. 
;; ;;They are able to do this by typing the name of the next park they are interested in.
(def current_topic nil)
(defn setp_bertramka [] (def current_topic :bertramka))
(defn setp_frantiskanska [] (def current_topic :frantiskanska-zahrada))
(defn setp_obora [] (def current_topic :obora-hvezda))
(defn setp_kampa [] (def current_topic :kampa))
(defn setp_kinskeho [] (def current_topic :kinskeho-zahrada))
(defn setp_klamovka [] (def current_topic :klamovka))
(defn setp_ladronka [] (def current_topic :ladronka))
(defn setp_letna [] (def current_topic :letna))
(defn setp_petrin [] (def current_topic :petrin))
(defn setp_riegrovy [] (def current_topic :riegrovy-sady))
(defn setp_stromovka [] (def current_topic :stromovka))
(defn setp_vysehrad [] (def current_topic :vysehrad))
(defn setp_treeID [] (def current_topic "tree_id"))


;; The user can only input yes or no and manage the decision tree's navigation.
;; Decision tree

(def tree_decision_tree {:q "Did the tree have spirally arranged leaves?"
                         :yes {:q "Was the tree about 50-70 feet?"
                               :yes {:q "Did it have flowers on it?"
                                     :yes "Oak tree"
                                     :no "Hornbeam"}
                               :no {:q "Was the tree small"
                                    :yes {:q "Did the tree have needles?"
                                          :yes "Was it a big tree"
                                          :no {:q "Did the needles grow in clusters?"
                                               :yes "Tilia"
                                               :no "Larch"}}
                                    :no {:q "Did it have accorns?"
                                         :yes {:q "Was it small?"
                                               :yes "Did you see any edible food growing"
                                               :no {:q "Needles?"
                                                    :yes "apple tree"
                                                    :no "Fir"}}}}}})


(def position_in_tree tree_decision_tree)

(defn tree_taxonomy [user_in_arr]
  (cond
    (arr_contains? user_in_arr "yes")
    (do
      (def position_in_tree (:yes position_in_tree))
      (if (= (:q position_in_tree) nil)
        (do
          (println position_in_tree)
          (println " : > ")
          
          (def current_topic nil))
        
        (println (:q position_in_tree))))
    (arr_contains? user_in_arr "no")
    (do
      (def position_in_tree (:no position_in_tree))
      (if (= (:q position_in_tree) nil)
        (do
          (println position_in_tree)
          (println " : > ")
          (def current_topic nil))
        (println (:q position_in_tree))))
    :else
    (do
      (println (:q position_in_tree))
      (println "Please reply with 'yes' or 'no' answers only"))))

(declare detect_keywords)

(defn topic_handler [user_in_arr]

  (cond
    (arr_contains? user_in_arr "bertramka")
    (do

      (setp_bertramka)


      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "frantiskanska")
    (do

      (setp_frantiskanska)


      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "obora")
    (do

      (setp_obora)


      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "kampa")
    (do

      (setp_kampa)

      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "kinskeho")
    (do
      (setp_kinskeho)


      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "klamovka")
    (do
      (setp_klamovka)

      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "ladronka")
    (do
      (setp_ladronka)

      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "letna")
    (do
      (setp_letna)

      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "petrin")
    (do
      (setp_petrin)

      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "riegrovy")
    (do
      (setp_riegrovy)

      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "stromovka")
    (do
      (setp_stromovka)

      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "vysehrad")
    (do
      (setp_vysehrad)

      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "tree")
    (setp_treeID)
    :else
    (if (= current_topic nil)
      (println "Please specify a park or type tree.")
      (detect_keywords user_in_arr current_topic)))
  (when (= current_topic "tree_id") (tree_taxonomy user_in_arr)))

;;Detection of keywords and questions
(defn detect_keywords [user_in_arr park]
  ;(println "DETECT KEYWORDS RAN")

  ;;Variables that restrict the user from producing repeated responses 
  ;;for the same two keywords
  (def i_par 0)
  (def i_wc 0)
  (def i_dogs 0)
  (def i_int 0)
  (def i_bik 0)
  (def i_ska 0)
  (def i_spo 0)
  (def i_tra 0)
  (def i_pla 0)
  (def i_web 0)

  (let [stop (dec (count user_in_arr))]
    (loop [i 0
           previous nil]
      (let [current (nth user_in_arr i)]
        (cond 
          (and (= i_wc 0) (contains? keys/set_wc (normalize_string current)))
          (reply_wc park)
          (and (= i_dogs 0) (contains? keys/set_dogs (normalize_string current)))
          (reply_dogs park)
          (and (= i_int 0) (contains? keys/set_attractions (normalize_string current)))
          (reply_attractions park)
          (and (= i_tra 0) (contains? keys/set_transportation (normalize_string current)))
          (reply_transportation park)
          (and (= i_bik 0) (contains? keys/set_biking (normalize_string current)))
          (reply_biking park)
          (and (= i_ska 0) (contains? keys/set_skating (normalize_string current)))
          (reply_skating park)
          (and (= i_spo 0) (contains? keys/set_sports (normalize_string current)))
          (reply_sports park)
          (and (= i_pla 0) (contains? keys/set_playground (normalize_string current)))
          (reply_playground park) 
          (and (= i_par 0) (contains? keys/set_parking (normalize_string current)))
          (reply_parking park)
          (and (= i_web 0) (or
                            (contains? keys/set_website (normalize_string current))
                            (contains? keys/set_website (list previous (normalize_string current)))))
          (reply_website park))
        (when (< i stop)
          (recur (inc i) (normalize_string current)))))))


;;Dialogue that will be printed for the user to see.
(defn dialogue_loop []
  (println ">Hello, what would you like to know about the parks in Prague? You can ask about trees too! Type exit or quit to terminate.")
  (println "================================================================")
  (loop [user_in (read-line)]
    (if (not (or (= user_in "exit") (= user_in "quit")))
      (do
        (println "----------------------------------------------------------------")
        (topic_handler (string_to_vector user_in))
        ;; (println "================================================================")
        (recur (read-line)))
      (do
        (println "----------------------------------------------------------------")
        (println ">Goodbye, we hope you enjoyed our service")))))

(dialogue_loop)