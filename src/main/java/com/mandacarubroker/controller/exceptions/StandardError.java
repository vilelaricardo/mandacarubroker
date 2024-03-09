package com.mandacarubroker.controller.exceptions;

import java.time.Instant;

public record StandardError(Instant instant, Integer status, String error, String message, String path) {
}
