# 调用执行方法的信息
doStartArthasCommonScriptCommand() {
  ARTHAS_COMMON_SCRIPT_COMMAND="${arthasCommonScriptCommand}"
  # maybe some get classloader is null sc -d java.lang.String
  # https://stackoverflow.com/questions/3306007/replace-a-string-in-shell-script-using-a-variable
  local scriptCommand=${ARTHAS_COMMON_SCRIPT_COMMAND//-c null/ }
  executeArthasCommand "${HOME}/opt/arthas/arthasCommonScriptCommand.out" "${scriptCommand}"
  banner_simple "you can get execute result in ${HOME}/opt/arthas/arthasCommonScriptCommand.out"
}

main() {

  banner_simple "arthas idea plugin  begin;start script path: $(pwd)/arthas-idea-plugin-common-execution-script.sh"

  check_permission

  installArthas

  if [ $? -ne 0 ]; then
    exit_on_err 1 "arthas install as.sh script error"
  fi

  if [ -z "${SELECT_VALUE}" ]; then
    reset_for_env
    select_pid
    SELECT_VALUE=${TARGET_PID}
  fi

  if [ -z "${SELECT_VALUE}" ]; then
    exit_on_err 1 "select target process by classname or JARfilename Target pid is empty"
  fi

  if [ "${SC_COMMAND}" ]; then
    banner_simple "first: get  class1oader hash value"
    getFirstClassLoaderHashValue
    if [ -z "${CLASSLOADER_HASH_VALUE}" ]; then
      exit_on_err 1 "not found classloader hash value,maybe class not load in jvm"
    fi
  fi

  echo " "
  banner_simple "last: execute arthas command"
  doStartArthasCommonScriptCommand

}

main "${@}"
