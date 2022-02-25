(ns coffee-shop.customer
  (:require [clojure.spec.alpha :as s]
            [coffee-shop.coffee :as c]))

(s/def :customer/satisfaction (s/and int?
                                     #(< % 101)
                                     #(> % 0)))
(s/def :order/item :coffee/drink-type)
(s/def :order/quantity int?)
(s/def :customer/order (s/coll-of (s/keys :req [:order/item
                                                :order/quantity])))
(s/def :customer/filled-order (s/map-of int?
                               (s/map-of keyword? :coffee/drink)))
(s/def :customer/customer (s/keys :req [:customer/order
                                        :customer/satisfaction]
                                  :opt [:customer/filled-order]))

(def testcustomer-before
  {:customer/order [{:order/item :coffee/latte
                     :order/quantity 2}
                    {:order/item :coffee/pour-over
                     :order/quantity 1}]
   :customer/satisfaction 50})

(def testcustomer-after
  {:customer/order [{:order/item :coffee/latte
                     :order/quantity 2}]
   :customer/filled-order {1 {:coffee/milk {:coffee/volume 400
                                            :coffee/temp 100
                                            :coffee/taste 10}
                              :coffee/espresso {:coffee/volume 40
                                                :coffee/temp 100
                                                :coffee/taste 10}}
                           2 {:coffee/milk {:coffee/volume 400
                                            :coffee/temp 100
                                            :coffee/taste 10}
                              :coffee/espresso {:coffee/volume 40
                                                :coffee/temp 100
                                                :coffee/taste 10}}}
   :customer/satisfaction 50})

(s/valid? :customer/customer testcustomer-before)
(s/valid? :customer/customer testcustomer-after)