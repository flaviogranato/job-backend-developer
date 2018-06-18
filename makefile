VER=$(shell git log -1 --pretty=format:"%H")

GIT_VERSION := $(shell git describe --abbrev=6 --dirty --always --tags)

CONTAINER_NAME = login:$(GIT_VERSION)


getver:
	@echo GIT_COMMIT=$(GIT_VERSION)

gradle:
	gradle prepareDocker

#-----------------------------------------------------------------------------
# default target
#-----------------------------------------------------------------------------

all   : ## Build the container - this is the default action
all: build

#-----------------------------------------------------------------------------
# build container
#-----------------------------------------------------------------------------

.built: . gradle
	docker build -t $(CONTAINER_NAME) build/docker
	@docker inspect -f '{{.Id}}' $(CONTAINER_NAME) > .built

build : ## build the container
build: .built


clean : ## delete the image from docker
clean: stop
	@$(RM) .built
	-docker rmi $(CONTAINER_NAME)

re    : ## clean and rebuild
re: clean all

#-----------------------------------------------------------------------------
# repository control
#-----------------------------------------------------------------------------

push  : ## Push container to remote repository
push: build
	docker push $(CONTAINER_NAME)

pull  : ## Pull container from remote repository - might speed up rebuilds
pull:
docker pull $(CONTAINER_NAME)

#-----------------------------------------------------------------------------
# test container
#-----------------------------------------------------------------------------

run   : ## Run the container as a daemon locally for testing
run: build stop
	docker run -d --name=$(INSTANCE_NAME) $(RUN_OPTS) $(CONTAINER_NAME)

stop  : ## Stop local test started by run
stop:
	-docker stop $(INSTANCE_NAME)
	-docker rm $(INSTANCE_NAME)

#-----------------------------------------------------------------------------
# supporting targets
#-----------------------------------------------------------------------------

help  : ## Show this help.
	@fgrep -h "##" $(MAKEFILE_LIST) | fgrep -v fgrep | sed -e 's/\\$$//' | sed -e 's/##//'

.PHONY : all build clean re push pull run stop help
