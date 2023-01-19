(ns ghko.cond
  (:require [ghko.reply :as ans]
            [ghko.keywords :as keys]
            [ghko.normalstring :as ns]))

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
          (and (= i_wc 0) (contains? keys/set_wc (ns/normalize_string current)))
          (ans/reply_wc park)
          (and (= i_dogs 0) (contains? keys/set_dogs (ns/normalize_string current)))
          (ans/reply_dogs park)
          (and (= i_int 0) (contains? keys/set_attractions (ns/normalize_string current)))
          (ans/reply_attractions park)
          (and (= i_tra 0) (contains? keys/set_transportation (ns/normalize_string current)))
          (ans/reply_transportation park)
          (and (= i_bik 0) (contains? keys/set_biking (ns/normalize_string current)))
          (ans/reply_biking park)
          (and (= i_ska 0) (contains? keys/set_skating (ns/normalize_string current)))
          (ans/reply_skating park)
          (and (= i_spo 0) (contains? keys/set_sports (ns/normalize_string current)))
          (ans/reply_sports park)
          (and (= i_pla 0) (contains? keys/set_playground (ns/normalize_string current)))
          (ans/reply_playground park) 
          (and (= i_par 0) (contains? keys/set_parking (ns/normalize_string current)))
          (ans/reply_parking park)
          (and (= i_web 0) (or
                            (contains? keys/set_website (ns/normalize_string current))
                            (contains? keys/set_website (list previous (ns/normalize_string current)))))
          (ans/reply_website park))
        (when (< i stop)
          (recur (inc i) (ns/normalize_string current)))))))

