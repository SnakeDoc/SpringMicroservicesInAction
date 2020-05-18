#!/usr/bin/env bash

#
# Builds all sub-projects, optionally runs Docker Compose.
# 
# Invokes command on all sub-projects:
#   mvn clean package docker:build && sync
#


#
# We're not sure what diretories will show up in here... so be explicit.
#
SERVICES=("confsvr" "licensing-service")

###############################################################################

ARGS=()

function validate_commands() {
	RESULT=false
	
	for command in "${ARGS[@]}"; do
		RESULT="$(validate_command ${command} ${1} ${2})"
		if [[ ${RESULT} == true ]]; then
			break
		fi
	done
	
	echo "${RESULT}"
}
#
# Arguments:
#	1) Command Line Argument
#	2) Command Short Flag
#	3) Command Long Flag
function validate_command () {
	echo $([[ "${1}" == "${2}" || "${1}" == "${3}" ]] && echo true || echo false)
}

#
# Command Line Arguments:
#	-b, --build		builds all services
#	-c, --config	fetches latest version of the config submodule
#	-r, --run		run the built containers in Docker Compose
#	
#
ARGS=("${@}")
DO_BUILD="$(validate_commands '-b' '--build')"
DO_CONFIG="$(validate_commands '-c' '--config')"
DO_RUN="$(validate_commands '-r' '--run')"
###############################################################################

function run() {

	if [ ! "${DO_RUN}" == true ] && [ ! "${DO_BUILD}" == true ] && [ ! "${DO_CONFIG}" == true ] ; then

		echo ""
		echo "Usage: ./dev.sh [OPTION]..."
		echo ""
		echo "	-b, --build	builds all services"
		echo "	-c, --config	fetches latest version of the config submodule"
		echo "	-r, --run	run the built containers in Docker Compose"
		echo ""
		
		exit_routine

	fi

	trap "exit_routine" ERR
	
	eval "$(ssh-agent -s)"
	ssh-add ~/.ssh/id_rsa

	HOME="$(pwd)"

	if [[ ${DO_CONFIG} == true ]]; then

		echo "${HOME}"
		echo "$(whoami)"
		cd "${HOME}/config" && pwd && git reset --hard HEAD && git pull

	fi

	if [[ ${DO_BUILD} == true ]]; then
		for service in "${SERVICES[@]}"; do
			cd "${HOME}/${service}"
			mvn clean package docker:build && sync
		done
	fi

	if [[ ${DO_RUN} == true ]]; then

		cd "${HOME}" && docker-compose.exe -f docker/common/docker-compose.yml up

	fi

}

function exit_routine() {
	exit 1
}

run

