package ce.pens.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkActivityEvent (
    @SerialName("flowId")
    var flowId: String,
    @SerialName("srcIp")
    var srcIp: String,
    @SerialName("srcPort")
    var srcPort: Int,
    @SerialName("dstIp")
    var dstIp: String,
    @SerialName("dstPort")
    var dstPort: Int,
    @SerialName("protocol")
    var protocol: Int,
    @SerialName("starttime")
    var startTime: String,
    @SerialName("flowDuration")
    var flowDuration: Long,
    @SerialName("fwdPktCount")
    var fwdPktCount: Long,
    @SerialName("bwdPktCount")
    var bwdPktCount: Long,
    @SerialName("fwdPktStats")
    var fwdPktStats: Double,
    @SerialName("bwdPktStats")
    var bwdPktStats: Double,
    @SerialName("fwdPktMax")
    var fwdPktMax: Double,
    @SerialName("fwdPktMin")
    var fwdPktMin: Double,
    @SerialName("fwdPktMean")
    var fwdPktMean: Double,
    @SerialName("fwdPktStd")
    var fwdPktStd: Double,
    @SerialName("bwdPktMax")
    var bwdPktMax: Double,
    @SerialName("bwdPktMin")
    var bwdPktMin: Double,
    @SerialName("bwdPktMean")
    var bwdPktMean: Double,
    @SerialName("bwdPktStd")
    var bwdPktStd: Double,
    @SerialName("flowBytePs")
    var flowBytePs: Double,
    @SerialName("flowPktPs")
    var flowPktPs: Double,
    @SerialName("flowIATMean")
    var flowIATMean: Double,
    @SerialName("flowIATStd")
    var flowIATStd: Double,
    @SerialName("flowIATMax")
    var flowIATMax: Double,
    @SerialName("flowIATMin")
    var flowIATMin: Double,
    @SerialName("fwdIATSum")
    var fwdIATSum: Double,
    @SerialName("fwdIATMean")
    var fwdIATMean: Double,
    @SerialName("fwdIATStd")
    var fwdIATStd: Double,
    @SerialName("fwdIATMax")
    var fwdIATMax: Double,
    @SerialName("fwdIATMin")
    var fwdIATMin: Double,
    @SerialName("bwdIATSum")
    var bwdIATSum: Double,
    @SerialName("bwdIATMean")
    var bwdIATMean: Double,
    @SerialName("bwdIATStd")
    var bwdIATStd: Double,
    @SerialName("bwdIATMax")
    var bwdIATMax: Double,
    @SerialName("bwdIATMin")
    var bwdIATMin: Double,
    @SerialName("fwdPshCount")
    var fwdPshCount: Int,
    @SerialName("bwdPshCount")
    var bwdPshCount: Int,
    @SerialName("fwdUrgCount")
    var fwdUrgCount: Int,
    @SerialName("bwdUrgCount")
    var bwdUrgCount: Int,
    @SerialName("fwdHeaderLen")
    var fwdHeaderLen: Double,
    @SerialName("bwdHeaderLen")
    var bwdHeaderLen: Double,
    @SerialName("fwdPktPS")
    var fwdPktPS: Double,
    @SerialName("bwdPktPS")
    var bwdPktPS: Double,
    @SerialName("pktLenMin")
    var PktLenMin: Double,
    @SerialName("pktLenMax")
    var PktLenMax: Double,
    @SerialName("pktLenMean")
    var pktLenMean: Double,
    @SerialName("pktLenStd")
    var pktLenStd: Double,
    @SerialName("pktLenVar")
    var pktLenVar: Double,
    @SerialName("finFlagCnt")
    var finFlagCnt: Int,
    @SerialName("synFlagCnt")
    var synFlagCnt: Int,
    @SerialName("rstFlagCnt")
    var rstFlagCnt: Int,
    @SerialName("pshFlagCnt")
    var pshFlagCnt: Int,
    @SerialName("ackFlagCnt")
    var ackFlagCnt: Int,
    @SerialName("urgFlagCnt")
    var urgFlagCnt: Int,
    @SerialName("cwrFlagCnt")
    var cwrFlagCnt: Int,
    @SerialName("eceFlagCnt")
    var eceFlagCnt: Int,
    @SerialName("upDownRatio")
    var upDownRatio: Double,
    @SerialName("avgPktSize")
    var avgPktSize: Double,
    @SerialName("fwdAvgSegmentSize")
    var fwdAvgSegmentSize: Double,
    @SerialName("bwdAvgSegmentSize")
    var bwdAvgSegmentSize: Double,
    @SerialName("fwdAvgBytesPBulk")
    var fwdAvgBytesPBulk: Long,
    @SerialName("fwdAvgPktPBulk")
    var fwdAbgPktPBulk: Long,
    @SerialName("fwdAvgBulkRate")
    var fwdAvgBulkRate: Long,
    @SerialName("bwdAvgBytesPBulk")
    var bwdAvgBytesPBulk: Long,
    @SerialName("bwdAvgPktPBulk")
    var bwdAvgPktPBulk: Long,
    @SerialName("bwdAvgBulkRate")
    var bwdAvgBulkRate: Long,
    @SerialName("fwdSubFlowPkt")
    var fwdSubFlowPkt: Long,
    @SerialName("fwdSubFlowBytes")
    var fwdSubFlowBytes: Long,
    @SerialName("bwdSubFlowPkt")
    var bwdSubFlowPkt: Long,
    @SerialName("bwdSubFlowBytes")
    var bwdSubFlowBytes: Long,
    @SerialName("initWinBytesFwd")
    var initWinBytesFwd: Int,
    @SerialName("initWinBytesBwd")
    var initWinBytesBwd: Int,
    @SerialName("fwdActData")
    var fwdActData: Long,
    @SerialName("fwdSegSize")
    var fwdSegSize: Long,
    @SerialName("flowActiveMean")
    var flowActiveMean: Double,
    @SerialName("flowActiveStd")
    var flowActiveStd: Double,
    @SerialName("flowActiveMax")
    var flowActiveMax: Double,
    @SerialName("flowActiveMin")
    var flowActiveMin: Double,
    @SerialName("flowIdleMean")
    var flowIdleMean: Double,
    @SerialName("flowIdleStd")
    var flowIdleStd: Double,
    @SerialName("flowIdleMax")
    var flowIdleMax: Double,
    @SerialName("flowIdleMin")
    var flowIdleMin: Double,
)

