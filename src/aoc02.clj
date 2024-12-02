(ns aoc02
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn inputs []
  (with-open [file (io/reader "src/02.txt")]
    (->> (line-seq file)
         (map (fn [line]
                (let [inputs (str/split line #"\W+")]
                  (map #(Integer/parseInt %) inputs))))
         (into []))))

; So, a report only counts as safe if both of the following are true: The levels are either all increasing or all decreasing.
; Any two adjacent levels differ by at least one and at most three.

(defn safe-delta? [a b]
  (let [diff (abs (- b a))]
    (<= 1 diff 3)))

(defn status [xs]
  (let [status (cond
                 (not (or (apply < xs)
                          (apply > xs)))
                 :unsafe
                 :else (loop [inputs xs]
                         (let [[a b & others] inputs]
                           (cond
                             (not b) :safe
                             (not (safe-delta? a b)) :unsafe
                             :else (if (seq others)
                                     (recur (drop 1 inputs))
                                     :safe)))))]
    {:status status
     :inputs xs}))

(defn iterate-status [xs]
  (->> xs
       (map-indexed (fn [idx _]
                      (status (apply concat [(take idx xs)
                                             (drop (inc idx) xs)]))))
       (map :status)
       (set)))

;; pt1
(->> (map status (inputs))
     (filter #(= :safe (:status %)))
     (count)
     (prn))

;; pt2
(->> (map iterate-status (inputs))
     (filter :safe)
     (count)
     (prn))
