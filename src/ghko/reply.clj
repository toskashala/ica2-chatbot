(ns ghko.reply 
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json]
            [ghko.preciates :as pred]
            [ghko.responses :as r]
            [ghko.normalstring :as ns]))
(def park-json (io/resource "park.json"))
  
(def parks_of_prague (json/read-json (slurp park-json)))

(defn reply_wc [park]
  (def i_wc 1)
  (cond
    (pred/wc? park)
    (println (:1 (:wc_yes r/responses)))
    (not (pred/wc? park))
    (println (:1 (:wc_no r/responses)))))

(defn reply_dogs [park]
  (def i_dogs 1)
  (cond
    (pred/dogs? park)
    (println (:1 (:dogs_yes r/responses)))
    (not (pred/dogs? park))
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
    (pred/biking? park)
    (println (:1 (:biking_yes r/responses)))
    (not (pred/biking? park))
    (println (:1 (:biking_no r/responses)))))

(defn reply_skating [park]
  (def i_ska 1)
  (cond
    (pred/skating? park)
    (println (:1 (:skating_yes r/responses)))
    (not (pred/skating? park))
    (println (:1 (:skating_no r/responses)))))

(defn reply_sports [park]
  (def i_spo 1)
  (cond
    (pred/sports? park)
    (println (:1 (:sports_yes r/responses)))
    (not (pred/sports? park))
    (println (:1 (:sports_no r/responses)))))


(defn reply_playground [park]
  (def i_pla 1)
  (cond
    (pred/playground? park)
    (println (:1 (:playground_yes r/responses)))
    (not (pred/playground? park))
    (println (:1 (:playground_no r/responses)))))

(defn reply_parking [park]
  (def i_par 1)
  (cond
    (pred/parking? park)
    (println (:1 (:parking_yes r/responses)))
    (not (pred/parking? park))
    (println (:1 (:parking_no r/responses)))))

(defn reply_website [park]
  (def i_web 1)
  (println "For more information" (ns/normalize_key park) "visit" (pred/website park)))
