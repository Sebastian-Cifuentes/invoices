(ns invoices.core)

(def filename "invoice.edn")

(def invoice (clojure.edn/read-string (slurp filename)))

(defn- items
  "Return items of an invoice"
  [invoice]
  (get invoice :invoice/items))

(defn- has-both
  "Verify when an item has both conditions"
  [item]
  (boolean (and (get item :retentionable/retentions) (get item :taxable/taxes) true))
  )

(defn- check-conditions
  "Check if tax or retention complies with the input condition"
  [vector-to-evaluate compare-number key]
  (loop [verify-evaluate vector-to-evaluate
         final-evaluate []]
    (if (nil? verify-evaluate)
      final-evaluate
      (let [[first & rest-ret] verify-evaluate]
        (recur rest-ret
               (when (= compare-number (get first key))
                 (conj final-evaluate first)
                 ))
        )
      )
    )
  )

(defn- check-both
  "Check if the validation in tax and the validation in retention, whether an item has tax a retention"
  [item]
  (when (nil? (and (check-conditions (get item :taxable/taxes) 19 :tax/rate) (check-conditions (get item :retentionable/retentions) 1 :retention/rate) true))
    identity
    )
  )

(defn- check
  "Spread the functions depending on the item has tax, retention or BOTH"
  [item]
  (cond
    (has-both item) (check-both item)
    (contains? item :taxable/taxes) (check-conditions (get item :taxable/taxes) 19 :tax/rate)
    (contains? item :retentionable/retentions) (check-conditions (get item :retentionable/retentions) 1 :retention/rate))
  )

(defn filter-items
  "Return an invoice with filtered items"
  [invoice]
  (->> (items invoice)
       (filter check)
       )
  )

; For executing the function.
(filter-items invoice)
