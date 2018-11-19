(ns cryptocurrency.core
  (:gen-class)
  (:use (java.security))
  (:import (java.security KeyPairGenerator)
           (javax.crypto Cipher)))

(defn generate-keypair
  []
  (let [key-pair-size 2048
        key-pair (KeyPairGenerator/getInstance "RSA")]
    (.initialize key-pair key-pair-size)
    (.genKeyPair key-pair)
    ))

(defn encrypt-message
  [message private-key]
  (let [byte-message (.getBytes message)
        cipher (Cipher/getInstance "RSA" )]
    (.init cipher Cipher/ENCRYPT_MODE private-key)
    (.doFinal cipher byte-message)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [encrypted-bytes (encrypt-message "hello" (.getPrivate (generate-keypair)))]
    (println (format "%x" (get encrypted-bytes 0))))
;(println (.getPublic (generate-keypair)))
  )

;; next steps
;; turn bytes to hex and print
;; turn hex back to bytes
;; write method to decrypt message
;; signing