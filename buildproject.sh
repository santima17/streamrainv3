#!/bin/bash

REPO_PATH='/home/santiago/tsi2workspace3/streamrainv3'

cd $REPO_PATH'/streamraindatalayer'
mvn clean
mvn install

cd $REPO_PATH'/streamrainfacades'
mvn clean
mvn install

cd $REPO_PATH'/streamrainbussines'
mvn clean
mvn install

cd $REPO_PATH'/streamrainservices'
mvn clean
mvn install

cd $REPO_PATH'/streamrainRESTUI'
mvn clean
mvn install

cd $REPO_PATH'/streamrainRESTUIGenerador'
mvn clean
mvn install