# Ignore inline messages which lay outside a diff's range of PR
github.dismiss_out_of_range_messages

# Make it more obvious that a PR is a work in progress and shouldn't be merged yet
warn("このPRはまだ作業中だよー") if github.pr_title.include? "[WIP]"

# ktlint
checkstyle_format.base_path = /repo
checkstyle_format.report 'app/build/reports/checkstyle.xml'