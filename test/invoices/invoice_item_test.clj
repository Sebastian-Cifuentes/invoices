(ns invoices.invoice-item-test
  (:require [invoices.invoice-item :as invoice-item]))

(use 'clojure.test)

(def invoice-with-discount #:invoice-item{:precise-quantity 5034 :precise-price 467 :discount-rate 20})
(def invoice-with-zeros #:invoice-item{:precise-quantity 0 :precise-price 0 :discount-rate 0})
(def invoice-with-doubles #:invoice-item{:precise-quantity 3.5 :precise-price 9.7 :discount-rate 35.5})
(def invoice-without-discount #:invoice-item{:precise-quantity 7248 :precise-price 54})
(def invoice-with-only-precise-price #:invoice-item{:precise-price 92})

(deftest verify-subtotal-result
  (is (= 1880702.4000000001 (invoice-item/subtotal invoice-with-discount)))
  )

(deftest verify-invoice-item-keys
  (let [item invoice-with-discount]
    (is (every? (partial contains? item) [:invoice-item/precise-quantity :invoice-item/precise-price])))
  )

(deftest verify-subtotal-without-discount
  (is (= 391392.0 (invoice-item/subtotal invoice-without-discount)))
  )

(deftest verify-zero-values
  (is (= 0.0 (invoice-item/subtotal invoice-with-zeros)))
  )

(deftest verify-doubles-values
  (is (= 21.89775 (invoice-item/subtotal invoice-with-doubles)))
  )

(deftest verify-discount-value
  (let [discount (:invoice-item/discount-rate invoice-with-discount)]
    (is (and (>= discount 0) (<= discount 100)))
    )
  )

;For running tests
(run-tests)