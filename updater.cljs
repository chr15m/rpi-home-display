(ns updater
  (:require
    ["fs" :as fs]
    [sitefox.html :refer [select-apply parse]]))

(print "Running updater.cljs")

(defn slurp-html [f]
  (-> (fs/readFileSync f) .toString))

(def template (slurp-html "template.html"))
(def weather (slurp-html "london-weather.html"))

(defn days-until-2025 []
  (let [target (js/Date. 2025 0 14)
        now (js/Date.)
        diff (- (.getTime target) (.getTime now))
        days (Math/ceil (/ diff (* 1000 60 60 24)))]
    days))

(defn process-images [forecast]
  (doseq [img (js/Array.from (.querySelectorAll forecast "img"))]
    (let [src (.getAttribute img "data-wf-src")]
      (.setAttribute img "src" src)))
  forecast)

(defn component:page [days daily-forecast hourly-forecast]
  [:<>
   [:h1 days " days left"]
   [:div
    {:dangerouslySetInnerHTML
     {:__html (.toString hourly-forecast)}}]
   [:div
    {:dangerouslySetInnerHTML
     {:__html (.toString daily-forecast)}}]])

(let [weather-dom (parse weather)
      daily-forecast (process-images
                       (.querySelector weather-dom ".dailyForecast"))
      hourly-forecast (process-images
                        (.querySelector weather-dom ".hourlyForecast"))
      page (select-apply
             template
             ["#app" :setHTML
              [component:page (days-until-2025)
               daily-forecast hourly-forecast]])]
  (fs/writeFileSync "index.html" (.toString page)))

(print "Done with updater.cljs")
