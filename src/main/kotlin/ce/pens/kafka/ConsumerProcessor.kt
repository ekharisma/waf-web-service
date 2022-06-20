package ce.pens.kafka

import ce.pens.constant.DecisionTreeModel
import ce.pens.entity.TrainFeature
import ce.pens.event.NetworkActivityEvent
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.util.concurrent.PriorityBlockingQueue

val featureQueue = PriorityBlockingQueue<DoubleArray>()

fun processMessage(message: NetworkActivityEvent) = runBlocking {
    val featureTrainingDeffered = async {
        parseEvent(message)
    }
    val featureTraining = featureTrainingDeffered.await()
    val inferenceArray = doubleArrayOf(
        featureTraining.dstPort.toDouble(),
        featureTraining.protocol.toDouble(),
        featureTraining.flowDuration.toDouble(),
        featureTraining.fwdPktCount.toDouble(),

        featureTraining.bwdPktCount.toDouble(),
        featureTraining.fwdPktStats,
        featureTraining.bwdPktStats,
        featureTraining.fwdPktMax,

        featureTraining.fwdPktMin,
        featureTraining.fwdPktMean,
        featureTraining.fwdPktStd,

        featureTraining.bwdPktMax,
        featureTraining.bwdPktMin,
        featureTraining.bwdPktMean,
        featureTraining.bwdPktStd,

        featureTraining.flowBytePs,
        featureTraining.flowPktPs,
        featureTraining.flowIATMean,
        featureTraining.flowIATStd,

        featureTraining.flowIATMax,
        featureTraining.flowIATMin,
        featureTraining.fwdIATSum,
        featureTraining.fwdIATMean,

        featureTraining.fwdIATStd,
        featureTraining.fwdIATMax,
        featureTraining.fwdIATMin,
        featureTraining.bwdIATSum,

        featureTraining.bwdIATMean,
        featureTraining.bwdIATStd,
        featureTraining.bwdIATMax,
        featureTraining.bwdIATMin,

        featureTraining.fwdPshCount.toDouble(),
        featureTraining.fwdHeaderLen,
        featureTraining.bwdHeaderLen,
        featureTraining.fwdPktPS,

        featureTraining.bwdPktPS,
        featureTraining.PktLenMin,
        featureTraining.PktLenMax,
        featureTraining.pktLenMean,

        featureTraining.pktLenStd,
        featureTraining.pktLenvar,
        featureTraining.finFlagCnt.toDouble(),
        featureTraining.synFlagCnt.toDouble(),

        featureTraining.rstFlagCnt.toDouble(),
        featureTraining.pshFlagCnt.toDouble(),
        featureTraining.ackFlagCnt.toDouble(),
        featureTraining.urgFlagCnt.toDouble(),

        featureTraining.eceFlagCnt.toDouble(),
        featureTraining.avgPktSize,
        featureTraining.fwdAvgSegmentSize,
        featureTraining.bwdAvgSegmentSize,

        featureTraining.fwdSubFlowPkt.toDouble(),
        featureTraining.fwdSubFlowBytes.toDouble(),
        featureTraining.bwdSubFlowPkt.toDouble(),
        featureTraining.bwdSubFlowBytes.toDouble(),

        featureTraining.initWinBytesFwd.toDouble(),
        featureTraining.initWinBytesBwd.toDouble(),
        featureTraining.fwdActData.toDouble(),
        featureTraining.fwdSegSize.toDouble(),

        featureTraining.flowActiveMean,
        featureTraining.flowActiveStd,
        featureTraining.flowActiveMax,
        featureTraining.flowActiveMin,

        featureTraining.flowIdleMean,
        featureTraining.flowIdleStd,
        featureTraining.flowIdleMax,
        featureTraining.flowIdleMin
    )

    val resultDeffered = async {
        DecisionTreeModel.score(inferenceArray)
    }

}

fun parseEvent(message: NetworkActivityEvent): TrainFeature {
    return TrainFeature(
        dstPort = message.dstPort,
        protocol = message.protocol,
        flowDuration = message.flowDuration,
        fwdPktCount = message.fwdPktCount,
        bwdPktCount = message.bwdPktCount,
        fwdPktStats = message.fwdPktStats,
        bwdPktStats = message.bwdPktStats,
        fwdPktMax = message.fwdPktMax,
        fwdPktMin = message.fwdPktMin,
        fwdPktMean = message.fwdPktMean,
        fwdPktStd = message.fwdPktStd,
        bwdPktMax = message.bwdPktMax,
        bwdPktMin = message.bwdPktMin,
        bwdPktMean = message.bwdPktMean,
        bwdPktStd = message.bwdPktStd,
        flowBytePs = message.flowBytePs,
        flowPktPs = message.flowPktPs,
        fwdIATSum = message.fwdIATSum,
        fwdIATMean = message.fwdIATMean,
        fwdIATStd = message.fwdIATStd,
        fwdIATMax = message.fwdIATMax,
        fwdIATMin = message.fwdIATMin,
        flowIATMean = message.flowIATMean,
        flowIATStd = message.flowIATStd,
        flowIATMax = message.flowIATMax,
        flowIATMin = message.flowIATMin,
        bwdIATSum = message.bwdIATSum,
        bwdIATMean = message.bwdIATMean,
        bwdIATStd = message.bwdIATStd,
        bwdIATMax = message.bwdIATMax,
        bwdIATMin = message.bwdIATMin,
        fwdPshCount = message.fwdPshCount,
        fwdHeaderLen = message.fwdHeaderLen,
        bwdHeaderLen = message.bwdHeaderLen,
        fwdPktPS = message.fwdPktPS,
        bwdPktPS = message.bwdPktPS,
        PktLenMin = message.PktLenMin,
        PktLenMax = message.PktLenMin,
        pktLenMean = message.pktLenMean,
        pktLenStd = message.pktLenStd,
        pktLenvar = message.pktLenVar,
        finFlagCnt = message.finFlagCnt,
        synFlagCnt = message.synFlagCnt,
        rstFlagCnt = message.rstFlagCnt,
        pshFlagCnt = message.pshFlagCnt,
        ackFlagCnt = message.ackFlagCnt,
        urgFlagCnt = message.urgFlagCnt,
        eceFlagCnt = message.eceFlagCnt,
        avgPktSize = message.avgPktSize,
        fwdAvgSegmentSize = message.fwdAvgSegmentSize,
        bwdAvgSegmentSize = message.bwdAvgSegmentSize,
        fwdSubFlowPkt = message.fwdSubFlowPkt,
        fwdSubFlowBytes = message.fwdSubFlowBytes,
        bwdSubFlowPkt = message.bwdSubFlowPkt,
        bwdSubFlowBytes = message.bwdSubFlowBytes,
        initWinBytesFwd = message.initWinBytesFwd,
        initWinBytesBwd = message.initWinBytesBwd,
        fwdActData = message.fwdActData,
        fwdSegSize = message.fwdSegSize,
        flowActiveMean = message.flowActiveMean,
        flowActiveStd = message.flowActiveStd,
        flowActiveMax = message.flowActiveMax,
        flowActiveMin = message.flowActiveMin,
        flowIdleMean = message.flowIdleMean,
        flowIdleStd = message.flowIdleStd,
        flowIdleMax = message.flowIdleMax,
        flowIdleMin = message.flowIdleMin,
    )
}
