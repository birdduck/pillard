(ns pillard.content.core
  (:require
    [pillard.shared :as shared]
    [goog.object :as gobj]))

(enable-console-print!)

(js/chrome.runtime.onMessage.addListener
  (fn [req sender sendResponse]
    (when (= (gobj/get req "message") "clicked_browser_action")
      (shared/capture-frame!))))
