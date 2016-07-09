.PHONY: test

test:
	boot test-once
	shellcheck scripts/*.sh
