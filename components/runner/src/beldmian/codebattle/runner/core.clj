(ns beldmian.codebattle.runner.core
  (:require
   [clojure.java.shell :as shell]))

(defn write-to-file [code extension]
  (let [file-path (str "/tmp/" (random-uuid) extension)] 
    (spit file-path code)
    file-path))

(defn run-python3-program [file-path]
  (shell/sh "python3" file-path))

(defn run-python3 [program]
  (-> (:code program)
      (write-to-file ".py")
      run-python3-program))

(defn compile-clang-program [file-path]
  (let [out-file (str "/tmp/" (random-uuid))]
    (shell/sh "clang" file-path "-o" out-file)
    out-file))

(defn execute-binary-program [file-path]
  (shell/sh file-path))

(defn run-clang [program]
  (-> (:code program)
      (write-to-file ".c")
      compile-clang-program
      execute-binary-program
      ))

(defn run [program] 
  (case (:language program)
    "python3" (run-python3 program)
    "clang" (run-clang program)))
