#!/bin/bash
set -e

env

gcloud beta run deploy service-currency-exchange
--image gcr.io/$PROJECT_ID/$ARTIFACT_ID:$ARTIFACT_VERSION
--args ARTIFACT_ID=$ARTIFACT_ID,ARTIFACT_VERSION=$ARTIFACT_VERSION,APPLICATION_PORT=$APPLICATION_PORT
--platform managed
--allow-unauthenticated
--cpu=1
--memory=512Mi
--region=us-central1 --quiet