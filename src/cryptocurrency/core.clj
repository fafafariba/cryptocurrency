(ns cryptocurrency.core
  (:gen-class)
  (:import (java.security KeyPairGenerator)
           (javax.crypto Cipher)
           (java.util Base64)
           (com.google.gson Gson)))

(defn generate-key-pair
  []
  (let [key-pair-size 2048
        key-pair (KeyPairGenerator/getInstance "RSA")]
    (.initialize key-pair key-pair-size)
    (.genKeyPair key-pair)))

(defn encrypt-message-to-bytes
  [message public-key]
  (let [byte-message (.getBytes message)
        cipher (Cipher/getInstance "RSA" )]
    (.init cipher Cipher/ENCRYPT_MODE public-key)
   (.doFinal cipher byte-message)))

(defn decrypt-message-to-string
  [message key]
  (let [decoder (Base64/getDecoder)
        decoded-message (.decode decoder message)
        cipher (Cipher/getInstance "RSA")]
    (.init cipher Cipher/DECRYPT_MODE key)
    (String. (.doFinal cipher decoded-message))))


(defn get-encrypted-encoded-string
  [key message]
  (let  [encrypted-bytes (encrypt-message-to-bytes message key)
         encoder (Base64/getEncoder)]
            (.encodeToString encoder encrypted-bytes)))

(defn sign-message
  [message key-pair]
  (let [encrypted-message (get-encrypted-encoded-string (.getPrivate key-pair) message)]
    {:message message :encrypted-message encrypted-message :public-key (.getPublic key-pair)}))

(defn to-json
  [signature]
  (let [gson (Gson. )]
    (.toJson gson signature)))

(defn validate-signed-message
  [signature]
  (let [public-key (:public-key signature)
        plain-message (:message signature)
        encrypted-message (:encrypted-message signature)]
    (if (= (decrypt-message-to-string encrypted-message public-key) plain-message)
      true
      false)
    ))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [key-pair (generate-key-pair)]
  ;(println (decrypt-message-to-string test (.getPrivate key-pair)))
  (println (sign-message "hello" key-pair))
  ;(println (validate-signed-message (sign-message "hello" key-pair)))
  ))

;; next steps
;; turn bytes to hex and print
;; turn hex back to bytes
;; write method to decrypt message
;; signing