package app.revanced.patches.youtube.button.autorepeat.fingerprints

import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import org.jf.dexlib2.AccessFlags

object AutoRepeatParentFingerprint : MethodFingerprint(
    returnType = "V",
    access = AccessFlags.PUBLIC or AccessFlags.FINAL,
    strings = listOf(
        "play() called when the player wasn't loaded.",
        "play() blocked because Background Playability failed"
    )
)