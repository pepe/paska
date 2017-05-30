(ns paska.handler.thing
  (:require [ataraxy.response :as response]
            [integrant.core :as integrant]
            [paska.boundary.thing :as thing]))


(defmethod integrant/init-key ::create [_ {:keys [db]}]
  (fn [{[_ thing] :ataraxy/result}]
    (let [id (thing/create-thing db thing)]
      [::response/created (str "/things/" id)])))


(defmethod integrant/init-key ::list [_ {:keys [db]}]
  (fn [_] [::response/ok (thing/list-things db)]))


(defmethod integrant/init-key ::search [_ {:keys [db]}]
  (fn [{[_ q] :ataraxy/result}]
    (if-let [things (thing/search-thing db q)]
      [::response/ok things]
      [::response/not-found {:error :not-found}])))


(defmethod integrant/init-key ::fetch [_ {:keys [db]}]
  (fn [{[_ id] :ataraxy/result}]
    (if-let [thing (thing/fetch-thing db id)]
      [::response/ok thing]
      [::response/not-found {:error :not-found}])))


(defmethod integrant/init-key ::delete [_ {:keys [db]}]
  (fn [{[_ thing] :ataraxy/result}]
    (let [count (thing/delete-thing db thing)]
      [::response/ok (str "Deleted " count " things")])))


