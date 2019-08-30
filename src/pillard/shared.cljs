(ns pillard.shared
  (:require
    [clojure.string :as str]
    [goog.dom :as gdom]
    [goog.object :as gobj]))

(def ^:dynamic *video-class* "html5-main-video")

(defn get-canvas [video]
  (let [canvas (doto (gdom/createElement "canvas")
                 (gobj/set "height" (gobj/get video "videoHeight"))
                 (gobj/set "width" (gobj/get video "videoWidth")))]
    (-> canvas
        (.getContext "2d")
        (.drawImage video 0 0 (gobj/get canvas "width") (gobj/get canvas "height")))
    canvas))

(defn download-blob! [canvas filename]
  (.toBlob canvas
    (fn [blob]
      (doto (gdom/createElement "a")
        (gobj/set "href" (js/URL.createObjectURL blob))
        (gobj/set "download" filename)
        (js/document.body.appendChild)
        (.click)
        (js/document.body.removeChild)))))

(defn get-filename [video]
  (let [t (Math/floor (gobj/get video "currentTime"))
        title (gobj/get (gdom/getElementByTagNameAndClass "h1" "title") "textContent")]
    (str title " (" t ").png")))

(defn capture-frame! []
  (when-some [video (gdom/getElementByClass *video-class*)]
    (-> video get-canvas (download-blob! (get-filename video)))))
