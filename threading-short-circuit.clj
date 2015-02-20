




(short=> (handle-validation submission)
         (lookup-user       ctx)
         (verify-password   sub)
         (setup-session     ctx)
         
         :on vector?)





; response is a specific implementation of short circuit
(response=> (handle-validation submission)
            (lookup-user       ctx)
            (verify-password   sub)
            (setup-session     ctx))