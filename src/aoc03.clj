(ns aoc03
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn input []
  (with-open [file (io/reader "src/03.txt")]
    (->> file
         (line-seq)
         (into [])
         (str/join ""))))

(defn execute [ops]
  (->> ops
       (map (fn [s]
              (let [[a b] (re-seq #"\d+" s)]
                (map parse-long [a b]))))
       (map (partial apply *))
       (reduce + 0)))

(defn part1 []
      (->> (input)
           (re-seq #"mul\(\d+,\d+\)")
           (execute)))

(defn part2 []
  ;; while no instructions, accumulate
  ;; on don't() skip following
  ;; on do() accumulate following
  (let [flip (re-matcher #"do\(\)|don\'t\(\)")]


    (->> (input)
         ())))


(part1)
