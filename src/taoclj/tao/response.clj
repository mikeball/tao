(ns taoclj.tao.response)


(def content-type-default nil)


(defn set-location "Sets a Location key in a map."
  [headers location]
  (if-not location headers
    (assoc headers "Location" location)))

(defn set-content-type "Sets a Content-Type key in a map."
  [headers default content-type]
  (assoc headers "Content-Type" (if-not content-type default
                                        content-type)))


(defn proxy-to-ring "converts tao response array to ring map"
  [response]
  (let [headers (second response)]
    {:status (first response)
     :headers (-> {}
                  (set-content-type content-type-default (headers :content-type))
                  (set-location (headers :location)))
     :cookies (headers :cookies)
     :body (nth response 2)}))

