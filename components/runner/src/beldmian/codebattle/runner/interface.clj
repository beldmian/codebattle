(ns beldmian.codebattle.runner.interface
  (:require [beldmian.codebattle.runner.core :as core]))

(defn run [program]
  (core/run program))
