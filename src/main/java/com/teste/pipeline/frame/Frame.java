package com.teste.pipeline.frame;

public class Frame implements ComparableFrame {
    private String frameContent;
    private boolean isLastFrame;
    private long sequenceNumber;
    private Integer layerSize;

    public Frame(String frameContent, long sequenceNumber,
                 boolean isLastFrame, Integer layerSize) {
        this.frameContent = frameContent;
        this.sequenceNumber = sequenceNumber;
        this.isLastFrame = isLastFrame;
        this.layerSize = layerSize;
    }

    public Frame(String frameContent, long sequenceNumber,
                 boolean isLastFrame) {
        this.frameContent = frameContent;
        this.sequenceNumber = sequenceNumber;
        this.isLastFrame = isLastFrame;
        this.layerSize = -1;
    }

    public void addProcessingMark(String mark) {
        this.frameContent += mark;
    }

    @Override
    public byte[] getData() {
        return frameContent.getBytes();
    }

    @Override
    public long getSequenceNumber() {
        return sequenceNumber;
    }

    @Override
    public boolean isLastFrame() {
        return isLastFrame;
    }

    @Override
    public boolean isLastFrameOfLayer() {
        return layerSize <= 1;
    }

    @Override
    public Void decreaseLayerSize() {
        synchronized (this) {
            layerSize--;
        }
        return null;
    }

    @Override
    public int compareTo(ComparableFrame comparableFrame) {
        //// TOMAR CUIDADOOOOOOO. Como o compareTo devolve um inteiro, se coloco um Long.MAX_VALUE
        // para o item final (com o intuito de deixar lá no fim do heap), vai dar ruim pq na hora de comparar
        // o cast do long Long.MAX_VALUE para inteiro joga 0 (ou -1, acho que eh -1), daí dando NEGATIVO no teste
        // abaixo e jogando o item para o começo do HEAP... como se fosse o menor valor de todos..................
        return (int) ( (sequenceNumber - comparableFrame.getSequenceNumber()));
    }

    public String getFrameContent() {
        return frameContent;
    }

    public static Frame createLastFrameWithLayerSize(Integer layerSize) {
        return new Frame("THE LAST ONE", Integer.MAX_VALUE, true, layerSize);
    }
}
