#!/bin/bash

set -euo pipefail

sonar-scanner -Dsonar.login=$SONAR_TOKEN -Dsonar.projectVersion=$TRAVIS_BUILD_NUMBER -Dsonar.organization=$SONAR_ORGANIZATION -Dsonar.projectKey=$SONAR_PROJECT_KEY -Dsonar.host.url=https://sonarqube.com