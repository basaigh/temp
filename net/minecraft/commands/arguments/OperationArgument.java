java.lang.ArrayIndexOutOfBoundsException: Array index out of range: 0
	at java.base/java.util.Vector.get(Vector.java:781)
	at com.strobel.assembler.metadata.MetadataResolver.resolve(MetadataResolver.java:82)
	at com.strobel.assembler.metadata.CoreMetadataFactory$UnresolvedType.resolve(CoreMetadataFactory.java:616)
	at com.strobel.decompiler.languages.java.ast.AstBuilder.convertType(AstBuilder.java:294)
	at com.strobel.decompiler.languages.java.ast.AstBuilder.convertType(AstBuilder.java:173)
	at com.strobel.decompiler.languages.java.ast.AstBuilder.convertType(AstBuilder.java:169)
	at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:664)
	at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
	at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
	at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
	at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
	at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
	at cuchaz.enigma.source.procyon.ProcyonDecompiler.getSource(ProcyonDecompiler.java:77)
	at cuchaz.enigma.EnigmaProject$JarExport.decompileClass(EnigmaProject.java:298)
	at cuchaz.enigma.EnigmaProject$JarExport.lambda$decompileStream$1(EnigmaProject.java:274)
	at java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:195)
	at java.base/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1655)
	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:484)
	at java.base/java.util.stream.ForEachOps$ForEachTask.compute(ForEachOps.java:290)
	at java.base/java.util.concurrent.CountedCompleter.exec(CountedCompleter.java:746)
	at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:290)
	at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1020)
	at java.base/java.util.concurrent.ForkJoinPool.scan(ForkJoinPool.java:1656)
	at java.base/java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1594)
	at java.base/java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:183)