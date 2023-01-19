(ns ghko.normalstring
              (:require[clojure.string :as str]))

(:require '[clojure.string :as str])
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