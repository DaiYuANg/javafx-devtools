val JvmArguments =
  listOf(
    "-XX:+UseZGC",
    "-XX:+ZGenerational",
    "-XX:+UseCompressedClassPointers",
    "-XX:+UseStringDeduplication",
    "-XX:+OptimizeStringConcat",
    "-XX:+UseCompressedOops",
    "-XX:MaxInlineLevel=32",
    "-XX:+AlwaysPreTouch",
    "-XX:+TieredCompilation",
    "-XX:SoftRefLRUPolicyMSPerMB=50",
    "-XX:+UseNUMA",
    "-Xmx256M",
  )
