(require 'bind-key)

(setq project-name "demosc")

;; System restarted with mount
(setq cider-refresh-before-fn "mount.core/stop"
      cider-refresh-after-fn "mount.core/start")

;; jack-in instructions for cljs
(setq cider-cljs-lein-repl "(do (require 'user) (user/start-cljs-dev!))")

;; There no tn/refresh for clojurescript, just call mount/stop and mount/start
(defun cider-refresh-with-cljs ()
  (interactive)
  (if (string= major-mode "clojurescript-mode")
        (cider-interactive-eval
         "(do (mount.core/stop) (mount.core/start))")
    (cider-refresh)))

;; Running cljs tests from cider for this project
(defun cider-run-all-tests ()
  (interactive)
  (if (string= major-mode "clojurescript-mode")
      (cider-interactive-eval
       (format
       "(do (require '%s.starter) (%s.starter/execute-tests))" project-name project-name))
    (cider-refresh)))

;; ------------ Recompiling contracts by shortcut

(setq command-compile-solidity "./compile-solidity.sh")

(defun directory-search-upward (directory file)
  (let ((parent-dir (file-truename (concat (file-name-directory directory) "../")))
        (current-path (if (not (string= (substring directory (- (length directory) 1)) "/"))
                         (concat directory "/" file)
                         (concat directory file))))
    (if (file-exists-p current-path)
        (file-name-directory current-path)
        (when (and (not (string= (file-truename directory) parent-dir))
                   (< (length parent-dir) (length (file-truename directory))))
          (directory-search-upward parent-dir file)))))

(defun buffer-whole-string (buffer)
  (with-current-buffer buffer
    (save-restriction
      (widen)
      (buffer-substring-no-properties buffer (point-min) (point-max)))))

(defun call-compile-solidity ()
  (interactive)
  (let ((buffer-name "*compile-solidity*")
        (default-directory (directory-search-upward
                            (file-name-directory (buffer-file-name))
                            "project.clj")))
    (if default-directory
        (progn
          (shell-command command-compile-solidity buffer-name)
          (with-current-buffer buffer-name
            (message (concat (buffer-string) "Done."))))
      (error "Root directory of project not found."))))

;; ------------

;; Hotkeys for local development
(bind-keys
 :map cider-mode-map
 ;; Fallback function with special reloading for cljs
 ("C-c C-x" . cider-refresh-with-cljs)
 ("M-/" . complete-symbol)
 ("C-c C-t t" . cider-run-all-tests)
 ("C-c C-w" . call-compile-solidity))

(bind-keys
 :map solidity-mode-map
 ("C-c C-w" . call-compile-solidity))
