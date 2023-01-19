(ns ghko.core
  (:require [clojure.java.io :as io] 
            [clojure.data.json :as json] 
            [ghko.normalstring :as ns]
            [ghko.cond :as cond]))
(:require '[clojure.string :as str])

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


(def tree_decision_tree 
  {:q "Does the tree have needles or scale-like leaves?"
   :yes {:q "Does the tree have cones?"
         :yes {:q "Does the tree have long needles in clusters?"
               :yes {:q "Is the tree evergreen?"
                      :yes "Pine tree"
                      :no "Larch tree"}
               :no {:q "Is the tree evergreen?"
                      :yes "Fir tree"
                      :no "Cedar tree"}}
         :no {:q "Is the tree evergreen?"
               :yes "Rosewood tree"
               :no "Cypress tree"}}
   :no {:q "Does the tree have leaves that are lobed or jagged?"
         :yes {:q "Are the leaves arranged alternately on the branches?"
               :yes "Birch tree"
               :no "Chestnut tree"}
         :no {:q "Does the tree have acorns?"
               :yes "Oak tree"
               :no "Hornbeam tree"}}})

(def position_in_tree tree_decision_tree)

(defn tree_taxonomy [user_in_arr]
  (cond
    (ns/arr_contains? user_in_arr "yes")
    (do
      (def position_in_tree (:yes position_in_tree))
      (if (= (:q position_in_tree) nil)
        (do
          (println position_in_tree)
          (println " : > ")
          
          (def current_topic nil))
        
        (println (:q position_in_tree))))
    (ns/arr_contains? user_in_arr "no")
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

(defn topic_handler [user_in_arr]

  (cond
    (ns/arr_contains? user_in_arr "bertramka")
    (do
      (setp_bertramka)


      (cond/detect_keywords user_in_arr current_topic))
    (ns/arr_contains? user_in_arr "frantiskanska")
    (do
      (setp_frantiskanska)


      (cond/detect_keywords user_in_arr current_topic))
    (ns/arr_contains? user_in_arr "obora")
    (do
      (setp_obora)


      (cond/detect_keywords user_in_arr current_topic))
    (ns/arr_contains? user_in_arr "kampa")
    (do
      (setp_kampa)

      (cond/detect_keywords user_in_arr current_topic))
    (ns/arr_contains? user_in_arr "kinskeho")
    (do
      (setp_kinskeho)
      
      (cond/detect_keywords user_in_arr current_topic))
    (ns/arr_contains? user_in_arr "klamovka")
    (do
      (setp_klamovka)

      (cond/detect_keywords user_in_arr current_topic))
    (ns/arr_contains? user_in_arr "ladronka")
    (do
      (setp_ladronka)

      (cond/detect_keywords user_in_arr current_topic))
    (ns/arr_contains? user_in_arr "letna")
    (do
      (setp_letna)

      (cond/detect_keywords user_in_arr current_topic))
    (ns/arr_contains? user_in_arr "petrin")
    (do
      (setp_petrin)

      (cond/detect_keywords user_in_arr current_topic))
    (ns/arr_contains? user_in_arr "riegrovy")
    (do
      (setp_riegrovy)

      (cond/detect_keywords user_in_arr current_topic))
    (ns/arr_contains? user_in_arr "stromovka")
    (do
      (setp_stromovka)

      (cond/detect_keywords user_in_arr current_topic))
    (ns/arr_contains? user_in_arr "vysehrad")
    (do
      (setp_vysehrad)

      (cond/detect_keywords user_in_arr current_topic))
    (ns/arr_contains? user_in_arr "tree")
    (setp_treeID)
    :else
    (if (= current_topic nil)
      (println "Please specify a park or type tree.")
      (cond/detect_keywords user_in_arr current_topic)))
  (when (= current_topic "tree_id") (tree_taxonomy user_in_arr)))


;;Dialogue that will be printed for the user to see.
(defn dialogue_loop []
  (println ">Hello, what would you like to know about the parks in Prague? You can ask about trees too! Type exit or quit to terminate.")
  (println "================================================================")
  (loop [user_in (read-line)]
    (if (not (or (= user_in "exit") (= user_in "quit")))
      (do
        (println "----------------------------------------------------------------")
        (topic_handler (ns/string_to_vector user_in))
        ;; (println "================================================================")
        (recur (read-line)))
      (do
        (println "----------------------------------------------------------------")
        (println ">Goodbye, we hope you enjoyed our service")))))

(dialogue_loop)