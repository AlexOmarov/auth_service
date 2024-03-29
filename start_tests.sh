#!/usr/bin/env bash

# Passing test/changed classes with it's path
# to formatted packages with appropriate tests
function get_test_packages_to_run() {

  files="$1"

  for file in ${files[@]}; do

    # If file is a class
    if [[ $file =~ \.java$ ]] || [[ $file =~ \.groovy$ ]] || [[ $file =~ \.kt$ ]]; then
        # Get package of the file
        package=$(get_package "$file")
        # Get tests in the same packages
        local tests_files=($(git ls-tree --name-only -r HEAD src/test/kotlin/$package src/test/groovy/$package src/test/java/$package))
        # If tests exist in the same package
        if [ ${#tests_files[@]} -ne 0 ]; then
           # Return formatted package of file
           format_package "$package"
        fi

    fi
  done
}

# Get package of the file
function get_package() {
  file="$1"
  echo "$file" | sed -e 's/src\/main\/java\///g' \
               | sed -e 's/src\/main\/kotlin\///g' \
               | sed -e 's/src\/test\/groovy\///g' \
               | sed -e 's/src\/test\/kotlin\///g' \
               | sed -e 's/.\w*.java$/\//g' \
               | sed -e 's/.\w*.kt$/\//g' \
               | sed -e 's/.\w*.groovy$/\//g'
}

# Format package as gradlew parameter
function format_package() {
  file="$1"
  echo "$file" | sed -e 's/\//./g' \
               | sed -e 's/.$/.*/g'

}

# Main function
function start_unit_tests() {

  # Getting files which have been changed in the last commit from current branch
  local changed_files=$(git diff-tree --no-commit-id --name-only -r HEAD)

  # Getting formatted packages which have tests for launch
  local test_packages_to_run=($(get_test_packages_to_run "$changed_files"))

  if [ ${#test_packages_to_run[@]} -ne 0 ]; then
    # Forming a parameter string for "gradlew :test" command
    local tests_filter=$(for i in ${test_packages_to_run[@]}; do echo --tests "$i"; done | sort -u)

    # Logging
    printf "Starting unit tests in the following list of packages:\n"
  	for i in "${tests_filter[@]}"; do echo  "${i//--tests /}"; done

  	# Running gradle test task with following filters
    #./gradlew :test $tests_filter
    sh gradlew :test ${tests_filter}
    else
    echo "There are no tests for changed files"
  fi
}

#Calling the main function
start_unit_tests