(ns paska.boundary.thing
  (:require [clojure.java.jdbc :as jdbc]
            [duct.database.sql]))


(defprotocol Things
  (create-thing [db thing])
  (list-things  [db])
  (fetch-thing  [db id])
  (search-thing [db q])
  (delete-thing [db q]))


(extend-protocol Things
  duct.database.sql.Boundary
  (create-thing [{db :spec} thing]
    (val (ffirst (jdbc/insert! db :things thing))))
  (list-things [{db :spec}]
    (jdbc/query db ["SELECT * FROM things"]))
  (fetch-thing [{db :spec} id]
    (first (jdbc/query db ["SELECT * FROM things WHERE id = ?" id])))
  (search-thing [{db :spec} q]
    (let [query (str "%" q "%")]
      (jdbc/query db ["SELECT * FROM things WHERE name LIKE ?" query])))
  (delete-thing [{db :spec} thing]
    (first (jdbc/delete! db :things ["id = ?" thing]))))
