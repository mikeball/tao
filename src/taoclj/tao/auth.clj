(ns taoclj.tao.auth
;;   (:require [clj-time.core :as time]
;;             [clj-time.format :as time-format]
;;             [taoclj.ticket :as ticket])
  )








; encrypted auth cookie

(defn make-cookie [session-id info]

  ; todo...

  )


; should this be turned into a "cookie-maker" function?
;; (defn issue-auth-cookie [session-id]
;;   (let [expires-at (time/plus (time/now) (time/minutes 5))]
;;      {conf/cookie-name {:http-only true
;;                         :expires (time-format/unparse
;;                                  (time-format/formatter "EEE, dd-MMM-yyyy HH:mm:ss zzz")
;;                                  expires-at)
;;                         :value session-id
;;                         ; todo encrypt
;;                         ; (ticket/issue conf/cookie-key session-id expires-at)

;;                         }}))


; expired cookie


(defn expired-cookie [cookie-name]
  {cookie-name {:value "expired"
                :expires "Thu, 01 Jan 1970 00:00:00 UTC"}})
