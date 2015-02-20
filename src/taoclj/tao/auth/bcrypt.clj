(ns taoclj.tao.auth.bcrypt
  (:import [org.mindrot.jbcrypt BCrypt]))



;; (defn generate-hash [cleartext]
  ; append/prefix hmac to password, then bcrypt

;;   (let [hashed (BCrypt/hashpw cleartext (BCrypt/gensalt))]
;;     (assoc user :password hashed)))


; is hmac on the passwords a good idea?? Not convinced because of...
; - what if you want to change your private key?
; - key rotation for the private key?





(defn passwords-match? [given known]

  ; append/prefix hmac to password, then generate hash value


   ; (BCrypt/checkpw given known)
   true
  )



