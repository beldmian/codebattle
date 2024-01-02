(ns beldmian.codebattle.http.core 
  (:require 
   [beldmian.codebattle.runner.interface :as runner]
   [ring.adapter.jetty :as jetty]
   [compojure.core :as compojure]
   [ring.middleware.json :as json])
  (:gen-class))

(defn run-handler [request]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body {:result (runner/run (:body request))}})

(compojure/defroutes app
  (compojure/POST "/run" _ run-handler))

(defn configured-json-body-middleware [handler]
  (json/wrap-json-body handler {:keywords? true :bigdecimals? true}))

(defn middlewares [app]
  (-> app
      configured-json-body-middleware 
      json/wrap-json-response))

(defn -main [& _]
  (jetty/run-jetty (middlewares app) {:port 8080}))