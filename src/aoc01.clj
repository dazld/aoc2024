(ns aoc01
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))


(defn lists []
  (->> (with-open [r (io/reader "src/01.txt")]
         (->> (line-seq r)
              (reduce (fn [[la lb] line]
                        (let [[a b] (str/split line #"\W+")]
                          [(conj la (Integer/parseInt a)) (conj lb (Integer/parseInt b))]))
                      [[] []])
              (map sort)))))

(let [[la lb] (lists)]
  (->> la
       (map-indexed (fn [idx item]
                      (-> ^Integer (- item (nth lb idx))
                          (Math/abs))))
       (reduce + 0)
       (prn)))

(let [[a b] (lists)
      freqs-b (frequencies b)]
  (->> a
       (keep (fn [n]
               (when-let [frequency (get freqs-b n)]
                 (* n frequency))))
       (reduce + 0)
       (prn)))
