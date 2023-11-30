# Invoices
You can filter items with tax in 19% or retention in 1%.
If an item has tax in 19% and retention in 1% won't work.
But, if the item has 19% in tax and a different percentage to 1% in retention or doesn't have it will work.
The same with the tax, if the item has 1% in retention and a different percentage to 19% in tax or doesn't have it will work.

Also, you can test an invoice built from JSON format.

Finally, there's a subtotal function where there are 6 tests for
verify its correctness function.

## Usage
- For filtering the invoice items, it must execute the next code in your REPL.
    ```
    (filter-items invoice)
    ```
  With this, you will call the `filter-items` function, where `invoice` arg is the `invoice.edn` read.


- For testing an invoice built from JSON format, it must execute the next code in
  your REPL.

  First, call the `convert-invoice-test` namespace.
  ```
  (require '[invoices.convert-invoice-test :as convert])
  ```
  Then, execute the `get-invoice` with JSON filename as arg.
    ```
    (convert/get-invoice "invoice.json")
    ```

- Finally, for testing the subtotal function, you can execute the next code in your REPL.

  First, call the `invoice-item-test` namespace.
  ```
  (require 'invoices.invoice-item-test)
  ```
  Then, execute the test with the help of `clojure.test` dependency.
  ```
  (clojure.test/run-tests 'invoices.invoice-item-test)
  ```

## License

Copyright © 2023 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
