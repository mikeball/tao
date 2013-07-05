(ns taoclj.tao.util)


(defn in?
  "Does an element exists in a sequence? Returns nil if nothing found."
  [sequence element] ;; potentially change the order of these
  (some #(= element %) sequence))

(defn any-matches?
  "Are there any matching elements between sequences?"
  [sequence1 sequence2]
  (some #(in? sequence1 %) sequence2))

;; (defn all-matches
;;   "Finds all matching elements between 2 sequences."
;;   [sequence1 sequence2]
;;   (filter #(in? sequence1 %) sequence2))

