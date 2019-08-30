(ns pillard.background.core
  (:require [goog.object :as gobj]))

(enable-console-print!)

(js/chrome.browserAction.onClicked.addListener
  (fn [tab]
    (js/chrome.tabs.sendMessage (gobj/get tab "id")
                                #js {:message "clicked_browser_action"})))
