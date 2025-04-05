# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# Set the path to your assignments repository
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-MENT2022.git;protocol=ssh;branch=main"

PV = "1.0+git${SRCPV}"
# Set to reference a specific commit hash in your assignment repo
SRCREV = "db9dc6009aa497ea0d038a0da8424e4c8c983dc7"

# Set the source directory to the server directory in your assignments repo
S = "${WORKDIR}/git/server"

# Add systemd or sysvinit support
inherit update-rc.d

# Add the aesdsocket application and start script to the package
FILES:${PN} += "\
    ${bindir}/aesdsocket \
    ${sysconfdir}/init.d/aesdsocket \
"


# Add required libraries and ensure GNU_HASH is added
TARGET_LDFLAGS += "-pthread -lrt -Wl,--hash-style=gnu"

do_configure () {
	:
}

do_compile () {
    echo "LDFLAGS: ${LDFLAGS}"
    oe_runmake LDFLAGS="${LDFLAGS}"
}

do_install () {
    # Install the aesdsocket binary
    install -d ${D}${bindir}
    install -m 0755 ${S}/aesdsocket ${D}${bindir}/

    # Install init script
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/aesdsocket-start-stop ${D}${sysconfdir}/init.d/aesdsocket
}

# Init script configuration
INITSCRIPT_NAME = "aesdsocket"
INITSCRIPT_PARAMS = "defaults 99"