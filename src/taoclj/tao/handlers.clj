(ns taoclj.tao.handlers)


(defmacro =>
  "Behaves as some-> except that it short circuits on vector? rather than nil?
  and returns the result vector, or result of final task"
  [init & tasks]
  (let [g (gensym)
        pstep (fn [step] `(if (vector? ~g) ~g (-> ~g ~step)))]
    `(let [~g ~init
           ~@(interleave (repeat g) (map pstep tasks))]
       ~g)))




;; ; my initial(overly complex) implementation of =>
;; (defmacro => [& tasks]

;;   (loop [nested-form nil rtasks (reverse tasks)]

;;     (if-not rtasks nested-form

;;       (let [task     (first rtasks)
;;             naked    (not (seq? task))
;;             taskname (if naked task (first task))

;;             call     (if naked `(~taskname ~'r)
;;                                `(~taskname ~'r ~@(next task)))

;;             top-level (= (count rtasks) 1)

;;             form     (cond (nil? nested-form)   ; bottom task
;;                            call

;;                            (= (count rtasks) 1) ; top level initial value
;;                            `(let [~'r ~task]
;;                               ~nested-form)

;;                            :else
;;                            `(let [~'r ~call]
;;                               (if (vector? ~'r) ~'r
;;                                 ~nested-form)))
;;             ]

;;         (recur form (next rtasks))

;;         ))))
