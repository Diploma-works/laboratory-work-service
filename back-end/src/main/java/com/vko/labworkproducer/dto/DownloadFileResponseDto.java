package com.vko.labworkproducer.dto;

public record DownloadFileResponseDto(byte[] data, String filename) {
}
