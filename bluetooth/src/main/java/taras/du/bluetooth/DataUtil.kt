package taras.du.bluetooth


/**
 * @param charactersToRemove The characters to remove from the receiving String.
 * @return A copy of the receiving String with the characters in `charactersToRemove` removed.
 */
fun String.removeAll(vararg characterToRemove: Char): String {
    return filterNot { characterToRemove.contains(it) }
}

