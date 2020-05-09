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

#
# If the command line flag -r, --run is used, optionally run the built containers
#
DO_RUN="$([[ ${1} == "-r" || ${1} == "--run" ]] && echo true || echo false)"
###############################################################################

trap "exit_routine" ERR

HOME="$(pwd)"

for service in "${SERVICES[@]}"; do
	cd "${HOME}/${service}"
	mvn clean package docker:build && sync
done

if [[ ${DO_RUN} ]]; then

	cd "${HOME}" && docker-compose.exe -f docker/common/docker-compose.yml up

fi

function exit_routine() {
	exit 1
}

