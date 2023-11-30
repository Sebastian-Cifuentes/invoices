(ns invoices.convert-invoice
  (:require
    [clojure.data.json :as json]
    [clojure.string :as str]
    [clojure.instant :as instant]
    ))

(defn- convert-date
  "Works for converting simple text to instate date. Ej: '01/10/2023' to #inst '2023-10-01T00:00:00.000-00:00'"
  [date]
  (instant/read-instant-date (str/join "-" (reverse (str/split date #"/"))))
  )

(defn- convert-customer
  "Convert customer from JSON to #:customer valid"
  [customer]
  (let [name (:company_name customer)
        email (:email customer)]
    #:customer {
                :name name
                :email email
                }
    )
  )

(defn- convert-tax
  "Convert each tax from taxes vector to #:tax valid"
  [tax]
  (let [category (:tax_category tax)
        rate (:tax_rate tax)]
    #:tax {
           :category (keyword (clojure.string/lower-case category))
           :rate (/ rate 100.0)
           }
    )
  )

(defn- convert-item
  "Convert each item from items vector to #:invoice-item valid"
  [item]
  (let [price (:price item)
        sku (:sku item)
        quantity (:quantity item)
        taxes (vec (map #(convert-tax %) (:taxes item)))
        ]
    #:invoice-item {
                    :price price
                    :quantity quantity
                    :sku sku
                    :taxes taxes
                    }
    )
  )

(defn- converted-data
  "Receive an invoice from json reader and build the invoice with it"
  [invoice]
  #:invoice
          {:issue-date (convert-date (:issue_date (:invoice invoice))),
           :customer   (convert-customer (:customer (:invoice invoice))),
           :items (vec (map #(convert-item %) (:items (:invoice invoice))))
           })

(defn get-invoice
  "Receive a valid name json in the directory and call ::invoice test"
  [name_json]

  (let [read-invoice (json/read (clojure.java.io/reader name_json) :key-fn keyword)
        invoice (converted-data read-invoice)]
    invoice
    )
  )