(ns updater
  (:require
    ["fs" :as fs]
    [sitefox.html :refer [select-apply]]))

(print "Running updater.cljs")

(def template (-> (fs/readFileSync "template.html") .toString))

(defn days-until-2025 []
  (let [target (js/Date. 2025 0 14)
        now (js/Date.)
        diff (- (.getTime target) (.getTime now))
        days (Math/ceil (/ diff (* 1000 60 60 24)))]
    days))

(defn component:page [days]
  [:h1 days " days left"])

(let [page (select-apply
             template
             ["#app" :setHTML
              [component:page (days-until-2025)]])]
  (fs/writeFileSync "index.html" (.toString page)))

(print "Done with updater.cljs")
