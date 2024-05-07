#!/bin/bash

# Function to retrieve semantic versioning from file
get_version() {
    version=$(cat version.txt)
    echo "$version"
}

# Function to increment the patch version
increment_patch() {
    current_version=$1
    # Incrementing patch version
    patch=$(( $(echo $current_version | awk -F. '{print $3}') + 1 ))
    new_version=$(echo $current_version | awk -F. -v OFS=. '{$3 = '$patch'} 1')
    echo "$new_version"
}

# Retrieve current version
current_version=$(get_version)

# Increment patch version
new_version=$(increment_patch "$current_version")

# Update version in file
echo "$new_version" > version.txt

# Execute the commands with the updated version
mvn package -Dmaven.test.skip=true -Dspring.profile.active=docker && \
docker buildx build -t taaesan/table-booking-service-k8:$new_version -t taaesan/table-booking-service-k8:latest . && \
docker push taaesan/table-booking-service-k8:$new_version && \
docker push taaesan/table-booking-service-k8:latest
