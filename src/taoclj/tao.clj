(ns taoclj.tao)



(defmacro short->
  "Short circuit pipeline/threading operator that shorts on given condition."
  [init & steps-and-conditions]

  (let [check (-> (take-last 2 steps-and-conditions)
                  ((fn [conditions] (if-not (and (= (count conditions) 2)
                                                 (= (first conditions) :on))
                                      (throw (Exception. "Invalid conditions function syntax."))

                                      (second conditions)))))

        result      (gensym)
        step-check  (fn [step] `(if (~check ~result) ~result (-> ~result ~step)))
        steps       (drop-last 2 steps-and-conditions)]

    `(let [~result ~init
           ~@(interleave (repeat result) (map step-check steps))]
       ~result)

    ))



;; (short-> {:name "bob"}
;;          ((fn [user]
;;            (if (= "bill" (:name user))
;;              [200 "bill not allowed"]
;;              (assoc user :step2 true))
;;          ))
;;          (assoc :step3 true)
;;          :on vector?
;;           )


(defmacro rsx->
  "Short circuit threading operator that shorts on a tao response."
  [& steps]
  `(short-> ~@steps :on vector?))


;; (rsx-> {:name "bill"}
;;        ((fn [user]
;;           (if (= "bill" (:name user))
;;             [200 "bill is not allowed"]
;;             (assoc user :step2 true))
;;           ))
;;        (assoc :step3 true))









