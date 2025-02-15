def breakDependencyOn(target):
    return target

def aeriform(target):
    name = target.replace("/", "").replace(":", "!")
    native.genquery(
        testonly = True,
        name = name + "_aeriform_sources.txt",
        expression = "labels(srcs, " + target + ")",
        scope = [target],
        tags = ["manual", "no-ide", "aeriform"],
    )

    native.genquery(
        testonly = True,
        name = name + "_aeriform_dependencies.txt",
        expression = "labels(deps, " + target + ")",
        scope = [target],
        tags = ["manual", "no-ide", "aeriform"],
    )

    native.genrule(
        testonly = True,
        name = name + "_aeriform_jdeps",
        outs = [name + "_aeriform_jdeps.txt"],
        tags = ["manual", "no-ide", "aeriform"],
        srcs = [target],
        cmd = " ".join([
            "$(JAVABASE)/bin/jdeps",
            "-v",
            "$(locations " + target + ")",
            "> \"$@\"",
        ]),
        toolchains = ["@bazel_tools//tools/jdk:current_host_java_runtime"],
    )

def aeriformAnnotations(**kwargs):
    name = kwargs.get("name")
    srcs = kwargs.get("srcs", [])

    cmd = "grep -A 1 \"^@OwnedBy\\|^@BreakDependencyOn\\|^@TargetModule\\|^@Deprecated\" "
    for src in srcs:
        cmd += "\"$(location %s)\" " % src
    cmd += "> \"$@\" || true"

    native.genrule(
        name = name + "_annotations",
        srcs = srcs,
        outs = [name + "_srcs_annotations.txt"],
        cmd = cmd,
        tags = ["manual", "no-ide", "aeriform"],
        visibility = ["//visibility:public"],
    )
