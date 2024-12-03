(ns aoc03
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn raw-ops []
  (with-open [file (io/reader "src/03.txt")]
    (->> file
         (line-seq)
         (into [])
         (str/join ""))))

(defn execute [start-value ops]
  (->> ops
       (map (fn [s]
              (let [[a b] (re-seq #"\d+" s)]
                (map parse-long [a b]))))
       (map (partial apply *))
       (reduce + start-value)))

(defn part1 [start-value data]
      (->> data
           (re-seq #"mul\(\d+,\d+\)")
           (execute start-value)))

(defn part2 [input]
  (loop [remaining input
         multiply? true
         total 0]
    (if (empty? remaining)
      total
      (let [do-match   (re-find #"do\(\)" remaining)
            dont-match (re-find #"don\'t\(\)" remaining)
            ops-match  (re-find #"mul\(\d+,\d+\)" remaining)]
        (cond
          do-match (recur (subs remaining (count do-match))
                          true
                          total)
          dont-match (recur (subs remaining (count dont-match))
                            false
                            total)
          (and ops-match multiply?)
          (let [[a b] (->> (re-seq #"\d+" ops-match)
                           (map parse-long))
                next-total (+ total (* a b))]
            (prn a b ops-match (subs remaining (count ops-match)))
            (recur (subs remaining (count ops-match))
                   multiply?
                   next-total))
          :else
          (recur (subs remaining 1) multiply? total))))))

;; :tick: 173529487
(part1 0 (raw-ops))

;; nope 3338216
(part2 (raw-ops))
